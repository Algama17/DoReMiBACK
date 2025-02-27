package dev.doremidevs.template_java.database;

import dev.doremidevs.template_java.enums.PianoNotes;
import dev.doremidevs.template_java.models.RecordingModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecordingRepository {

    public void initializeDatabase() {

        try (Connection conn = DatabaseConnection.getConnection();
            var stmt = conn.createStatement()) {

                stmt.execute("CREATE TABLE IF NOT EXISTS Recordings (" + "id INT PRIMARY KEY AUTO_INCREMENT, " + "name VARCHAR(255), " + "notes TEXT" );
            
                System.out.println("Base de datos inicializada");
            }
             catch (SQLException e) 
             {e.printStackTrace();}
    }

    public void saveRecording(String name, List<String> notes) {
        String query = "INSERT INTO Recordings (name, notes) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setString(2,String.join(",", notes));
            pstmt.executeUpdate();
            System.out.println("Grabación guardada: " + name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<RecordingModel<?>> getRecordings() {
        List <RecordingModel<?>> recordings = new ArrayList<>();
        String query = "SELECT id, name, notes FROM Recordings";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String notes = rs.getString("notes");
                    List<String> noteList = new ArrayList<>(List.of(notes.split(",")));
                   RecordingModel<PianoNotes> recording = new RecordingModel<>(id, name, new ArrayList<>());
                for (String noteName : noteList) {
                    for (PianoNotes note : PianoNotes.values()) {
                        if (note.getNoteName().equals(noteName)) {
                            recording.createRecording(note);
                            break;
                        }
                    }
                }
                recordings.add(recording);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recordings;
    }
}